package PrismaMod.actions.CardActions.Illya.Orbs;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX.DefendSFX;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_ONE;

public class ReinForceAction extends AbstractCardAction {

    public boolean upgraded;
    public float delay;
    public boolean hit;
    public int block;
    public ReinForceAction(AbstractPlayer p, boolean upgraded, int block){
        this.p = p;
        this.handler = getHandler(p);
        this.target = p;
        this.sfx = new DefendSFX(p);
        this.delay = this.sfx.getStallTimer();
        this.duration = this.sfx.duration;
        AbstractDungeon.effectsQueue.add(sfx);
        this.hit = false;
        this.upgraded = upgraded;
        this.block = block;
        handler.setAnimation(SKILL_ONE);
        //handler.setAnimtion(DEFEND_ANIM);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            int numOrbs = p.maxOrbs;
            if (!upgraded) {
                for (int i = 0; i < numOrbs; i++) {
                    AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, block));
                }
            } else if (upgraded) {
                for (int i = 0; i < numOrbs * 2; i++) {
                    AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, block));
                }
            }
            this.hit = true;
        }
        this.tickDuration();
    }
}
