package PrismaMod.actions.CardActions.Illya.Orbs;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX.DefendSFX;
import PrismaMod.orbs.*;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_ONE;

public class UberChannelAction extends AbstractCardAction {

    public float delay;
    public boolean hit;
    public UberChannelAction(AbstractPlayer p){
        this.p = p;
        this.handler = getHandler(p);
        this.target = p;
        this.sfx = new DefendSFX(p);
        this.delay = this.sfx.getStallTimer();
        this.duration = this.sfx.duration;
        AbstractDungeon.effectsQueue.add(sfx);
        this.hit = false;
        handler.setAnimation(SKILL_ONE);
        //handler.setAnimtion(CHANNEL_ANIM);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            AbstractDungeon.actionManager.addToBottom(new EvokeAllOrbsAction());
            int numMaxOrbs = p.maxOrbs;
            int rand;
            for (int i = 0; i < numMaxOrbs; i++) {
                rand = (int) (Math.random() * 6);
                switch (rand) {
                    case 0:
                        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaAssassinOrb(), true));
                        break;
                    case 1:
                        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaAttackOrb(), true));
                        break;
                    case 2:
                        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaDefendOrb(), true));
                        break;
                    case 3:
                        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaPoisonOrb(), true));
                        break;
                    case 4:
                        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaTestamentOrb(), true));
                        break;
                    case 5:
                        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaZweiOrb(), true));
                        break;
                }
            }
            this.hit = true;
        }
        this.tickDuration();
    }
}
