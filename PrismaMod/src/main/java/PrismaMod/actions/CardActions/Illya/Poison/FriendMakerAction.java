package PrismaMod.actions.CardActions.Illya.Poison;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX.AttackSFX;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_ONE;

public class FriendMakerAction extends AbstractCardAction {

    public int poison;
    public int damage;
    public DamageInfo.DamageType damageType;
    public float delay;
    public boolean hit;
    public FriendMakerAction(AbstractPlayer p, AbstractCreature target, int poison, int damage, DamageInfo.DamageType damageType){
        this.p = p;
        this.handler = getHandler(p);
        this.target = target;
        this.sfx = new AttackSFX(p, target);
        this.delay = this.sfx.getStallTimer();
        this.duration = this.sfx.duration;
        AbstractDungeon.effectsQueue.add(sfx);
        this.hit = false;
        this.poison = poison;
        this.damage = damage;
        this.damageType = damageType;
        handler.setAnimation(SKILL_ONE);
        //handler.setAnimtion(BLAST_ATTACK);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            AbstractDungeon.actionManager.addToTop(new DamageAction(target, new DamageInfo(p, damage, damageType)));//, AbstractGameAction.AttackEffect.NONE)); // The animation the damage action uses to hit.
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, target, new PoisonPower(target, target, poison)));//,1, AbstractGameAction.AttackEffect.NONE));
            this.hit = true;
        }
        this.tickDuration();
    }
}
