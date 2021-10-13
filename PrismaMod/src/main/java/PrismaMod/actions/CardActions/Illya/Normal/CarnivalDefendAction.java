package PrismaMod.actions.CardActions.Illya.Normal;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX.DefendSFX;
import PrismaMod.orbs.IllyaDefendOrb;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_THREE;

public class CarnivalDefendAction extends AbstractCardAction {

    private int block;
    public float delay;
    public boolean hit;
    public int numOrbs;
    public CarnivalDefendAction(AbstractPlayer p, int block, int numOrbs){
        this.target = p;
        this.handler = getHandler(p);
        this.sfx = new DefendSFX(p);
        AbstractDungeon.effectsQueue.add(sfx);
        this.block = block;
        this.duration = this.sfx.duration;
        //handler.setAnimtion(DEFEND_ANIM);
        this.delay = this.sfx.getStallTimer();
        this.numOrbs = numOrbs;
        handler.setAnimation(SKILL_THREE);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            //logger.info("HIT");
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(target, target, block));
            for (int i = 0; i < numOrbs; i++) {
                AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaDefendOrb(), true));
            }
            this.hit = true;
        }

        //handler.animationUpdate();
        this.tickDuration();
    }
}
