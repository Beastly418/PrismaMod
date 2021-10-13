package PrismaMod.actions.CardActions.Illya.Poison;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.PoisonSFX.FeedSFX;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_ONE;

public class FeedAction extends AbstractCardAction {

    public float delay;
    public boolean hit;
    public int healcap;
    public int damage;
    public DamageInfo.DamageType damageType;
    public FeedAction(AbstractPlayer p, AbstractCreature target, int healcap, int damage, DamageInfo.DamageType damageType){
        this.p = p;
        this.handler = getHandler(p);
        this.target = target;
        this.sfx = new FeedSFX(target);
        this.delay = this.sfx.getStallTimer();
        this.duration = this.sfx.duration;
        AbstractDungeon.effectsQueue.add(sfx);
        this.hit = false;
        this.healcap = healcap;
        this.damage = damage;
        this.damageType = damageType;
        handler.setAnimation(SKILL_ONE);
        //handler.setAnimtion(CHANNEL_ANIM);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            AbstractDungeon.actionManager.addToTop(new DamageAction(target, new DamageInfo(p, damage, damageType), AbstractGameAction.AttackEffect.NONE)); // The animation the damage action uses to hit.
            if (target.hasPower(PoisonPower.POWER_ID)) {
                AbstractPower pow = target.getPower(PoisonPower.POWER_ID);
                int num = 0;
                if (pow.amount > healcap) {
                    num = healcap;
                } else {
                    num = pow.amount;
                }
                AbstractDungeon.actionManager.addToTop(new HealAction(p, p, num));
            }
            this.hit = true;
        }
        this.tickDuration();
    }
}
