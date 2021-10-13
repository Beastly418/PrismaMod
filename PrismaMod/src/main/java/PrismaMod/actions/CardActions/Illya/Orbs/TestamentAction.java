package PrismaMod.actions.CardActions.Illya.Orbs;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX.DefendSFX;
import PrismaMod.orbs.IllyaTestamentOrb;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_THREE;

public class TestamentAction extends AbstractCardAction {

    public float delay;
    public boolean hit;
    public TestamentAction(AbstractPlayer p){
        this.p = p;
        this.handler = getHandler(p);
        this.target = p;
        this.sfx = new DefendSFX(p);
        this.delay = this.sfx.getStallTimer();
        this.duration = this.sfx.duration;
        AbstractDungeon.effectsQueue.add(sfx);
        this.hit = false;
        handler.setAnimation(SKILL_THREE);
        //handler.setAnimtion(INSTALL_ANIM);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            int numMaxOrbs = p.maxOrbs;
            AbstractDungeon.actionManager.addToBottom(new EvokeAllOrbsAction());
            for (int i = 0; i < numMaxOrbs; i++) {
                AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaTestamentOrb(), true));
            }
            this.hit = true;
        }
        this.tickDuration();
    }
}
