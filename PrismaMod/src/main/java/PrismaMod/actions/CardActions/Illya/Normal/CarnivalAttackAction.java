package PrismaMod.actions.CardActions.Illya.Normal;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX.CarnivalAttackSFX;
import PrismaMod.orbs.IllyaAttackOrb;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_ONE;

public class CarnivalAttackAction extends AbstractCardAction {

    public int damage;
    public float delay;
    public DamageInfo.DamageType damageType;
    public boolean hit;
    public CarnivalAttackAction(AbstractPlayer p, AbstractCreature target, int damage, DamageInfo.DamageType damageType){
        this.p = p;
        this.handler = getHandler(p);
        this.sfx = new CarnivalAttackSFX(p, target);
        AbstractDungeon.effectsQueue.add(sfx);
        this.target = target;
        this.damage = damage;
        this.damageType = damageType;
        this.duration = this.sfx.duration;
        this.delay = this.sfx.getStallTimer();// - this.sfx.getEndStall()/4F;
        this.hit = false;
        //handler.setAnimtion(CHANNEL_ANIM);
        handler.setAnimation(SKILL_ONE);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            //logger.info("HIT");
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaAttackOrb(), true));
            target.damage(new DamageInfo(p, damage, damageType));
            this.hit = true;
        }
        this.tickDuration();
    }
}
