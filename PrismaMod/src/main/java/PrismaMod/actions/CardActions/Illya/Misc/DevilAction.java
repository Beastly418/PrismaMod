package PrismaMod.actions.CardActions.Illya.Misc;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.MiscSFX.DevilSFX;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_ONE;

public class DevilAction extends AbstractCardAction {

    public float delay;
    public boolean hit;
    public int draw;
    public int damage;
    public DamageInfo.DamageType damageType;
    public DevilAction(AbstractPlayer p, AbstractCreature target, int damage, DamageInfo.DamageType damageType, int draw){
        this.p = p;
        this.handler = getHandler(p);
        this.sfx = new DevilSFX(target);
        AbstractDungeon.effectsQueue.add(sfx);
        this.target = target;
        this.damageType = damageType;
        this.damage = damage;
        this.duration = this.sfx.duration;
        this.delay = this.sfx.getStallTimer();// - this.sfx.getEndStall()/4F;
        this.hit = false;
        this.draw = draw;
        handler.setAnimation(SKILL_ONE);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            target.damage(new DamageInfo(p, damage, damageType));
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(p, draw));
            this.hit = true;
        }
        this.tickDuration();
    }
}
