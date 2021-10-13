package PrismaMod.actions.CardActions.Illya.Orbs;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX.DefendSFX;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.ATTACK_TWO;

public class UncommonEvokeAction extends AbstractCardAction {

    public float delay;
    public boolean hit;
    public UncommonEvokeAction(AbstractPlayer p){
        this.p = p;
        this.handler = getHandler(p);
        this.target = p;
        this.sfx = new DefendSFX(p);
        this.delay = this.sfx.getStallTimer();
        this.duration = this.sfx.duration;
        AbstractDungeon.effectsQueue.add(sfx);
        this.hit = false;
        handler.setAnimation(ATTACK_TWO);
        //handler.setAnimtion(ORB_ATTACK);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            AbstractOrb tmp = p.orbs.get(0);
            AbstractDungeon.actionManager.addToBottom(new EvokeOrbAction(1));
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(tmp));
            this.hit = true;
        }
        this.tickDuration();
    }
}
