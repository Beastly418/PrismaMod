package PrismaMod.actions.CardActions.Illya.Orbs;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX.DefendSFX;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_ONE;

public class ChanneledBlockAction  extends AbstractCardAction {

    private int block;
    public float delay;
    public boolean hit;
    public ChanneledBlockAction(AbstractPlayer p, int block){
        this.p = p;
        this.handler = getHandler(p);
        this.target = p;
        this.sfx = new DefendSFX(p);
        this.delay = this.sfx.getStallTimer();
        this.duration = this.sfx.duration;
        this.block = block;
        AbstractDungeon.effectsQueue.add(sfx);
        this.hit = false;
        handler.setAnimation(SKILL_ONE);
        //handler.setAnimtion(CHANNEL_ANIM);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
            this.hit = true;
        }
        this.tickDuration();
    }
}
