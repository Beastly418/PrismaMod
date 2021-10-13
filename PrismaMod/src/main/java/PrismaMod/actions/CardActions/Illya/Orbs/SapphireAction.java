package PrismaMod.actions.CardActions.Illya.Orbs;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_THREE;

public class SapphireAction extends AbstractCardAction {

    public int draw;
    public int orbInc;
    public float delay;
    public boolean hit;
    public SapphireAction(AbstractPlayer p, int orbInc, int draw){
        this.p = p;
        this.handler = getHandler(p);
        this.target = p;
        this.duration = 0;//this.sfx.duration;
        this.delay = 0;//this.sfx.getStallTimer();// - this.sfx.getEndStall()/4F;
        this.hit = false;
        this.draw = draw;
        this.orbInc = orbInc;
        handler.setAnimation(SKILL_THREE);
        //handler.setAnimtion(CHANNEL_ANIM);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            AbstractDungeon.actionManager.addToBottom(new IncreaseMaxOrbAction(this.orbInc));
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(p, this.draw));
            this.hit = true;
        }
        this.tickDuration();
    }
}
