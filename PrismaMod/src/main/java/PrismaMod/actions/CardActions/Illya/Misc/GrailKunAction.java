package PrismaMod.actions.CardActions.Illya.Misc;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.MiscSFX.GrailKunSFX;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.unique.AttackFromDeckToHandAction;
import com.megacrit.cardcrawl.actions.unique.SkillFromDeckToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_ONE;

public class GrailKunAction extends AbstractCardAction {

    public float delay;
    public boolean hit;
    public int number;

    public GrailKunAction(AbstractPlayer p, int number){
        this.p = p;
        this.handler = getHandler(p);
        this.sfx = new GrailKunSFX(p);
        AbstractDungeon.effectsQueue.add(sfx);
        this.target = p;
        this.duration = this.sfx.duration;
        this.delay = this.sfx.getStallTimer();// - this.sfx.getEndStall()/4F;
        this.hit = false;
        this.number = number;
        handler.setAnimation(SKILL_ONE);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            AbstractDungeon.actionManager.addToTop(new AttackFromDeckToHandAction(number));
            AbstractDungeon.actionManager.addToTop(new SkillFromDeckToHandAction(number));
            this.hit = true;
        }
        this.tickDuration();
        if (this.duration <= 0){
            //handler.resetAnimation();
        }
    }
}
