package PrismaMod.actions.CardActions.Illya.Misc;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.MiscSFX.BrideSFX;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_ONE;

public class BrideAction extends AbstractCardAction {

    public float delay;
    public boolean hit;
    public int draw;

    public BrideAction(AbstractPlayer p, int draw){
        this.p = p;
        this.handler = getHandler(p);
        this.sfx = new BrideSFX(p);
        AbstractDungeon.effectsQueue.add(sfx);
        this.target = p;
        this.duration = this.sfx.duration;
        this.delay = this.sfx.getStallTimer();// - this.sfx.getEndStall()/4F;
        this.hit = false;
        this.draw = draw;
        //handler.setAnimtion(CHANNEL_ANIM);
        handler.setAnimation(SKILL_ONE);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.target, this.draw));
            this.hit = true;
        }
        this.tickDuration();
    }
}
