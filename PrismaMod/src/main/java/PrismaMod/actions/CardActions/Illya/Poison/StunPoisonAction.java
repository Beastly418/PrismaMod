package PrismaMod.actions.CardActions.Illya.Poison;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX.AttackSFX;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.ATTACK_ONE;

public class StunPoisonAction extends AbstractCardAction {

    public DamageInfo.DamageType damageType;
    public int damage;
    public float delay;
    public boolean hit;
    public StunPoisonAction(AbstractPlayer p, AbstractCreature target,  int damage, DamageInfo.DamageType damageType){
        this.p = p;
        this.handler = getHandler(p);
        this.target = target;
        this.sfx = new AttackSFX(p, target);
        this.delay = this.sfx.getStallTimer();
        this.duration = this.sfx.duration;
        AbstractDungeon.effectsQueue.add(sfx);
        this.hit = false;
        this.damage = damage;
        this.damageType = damageType;
        handler.setAnimation(ATTACK_ONE);
        //handler.setAnimtion(BLAST_ATTACK);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            AbstractDungeon.actionManager.addToTop(new DamageAction(target, new DamageInfo(p, damage, damageType)));//, AbstractGameAction.AttackEffect.NONE)); // The animation the damage action uses to hit.
            if (target.hasPower(PoisonPower.POWER_ID)) {
                AbstractDungeon.actionManager.addToTop(new DamageAction(target, new DamageInfo(p, damage, damageType)));//, AbstractGameAction.AttackEffect.NONE)); // The animation the damage action uses to hit.
            }
            this.hit = true;
        }
        this.tickDuration();
    }
}
