package PrismaMod.actions.CardActions.Illya.Orbs;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX.DefendSFX;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeWithoutRemovingOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_ONE;

public class DualEvokeAction extends AbstractCardAction {

    public boolean upgraded;
    public float delay;
    public boolean hit;
    public DualEvokeAction(AbstractPlayer p, boolean upgraded){
        this.p = p;
        this.handler = getHandler(p);
        this.target = p;
        this.sfx = new DefendSFX(p);
        this.delay = this.sfx.getStallTimer();
        this.duration = this.sfx.duration;
        AbstractDungeon.effectsQueue.add(sfx);
        this.hit = false;
        this.upgraded = upgraded;
        handler.setAnimation(SKILL_ONE);
        //handler.setAnimtion(ORB_ATTACK);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            AbstractDungeon.actionManager.addToBottom(new EvokeWithoutRemovingOrbAction(1));
            AbstractDungeon.actionManager.addToBottom(new EvokeOrbAction(1));
            if (upgraded) {
                AbstractDungeon.actionManager.addToBottom(new EvokeWithoutRemovingOrbAction(1));
                AbstractDungeon.actionManager.addToBottom(new EvokeOrbAction(1));
            }
            this.hit = true;
        }
        this.tickDuration();
    }
}
