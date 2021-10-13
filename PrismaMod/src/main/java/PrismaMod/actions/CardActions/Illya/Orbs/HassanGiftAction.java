package PrismaMod.actions.CardActions.Illya.Orbs;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX.DefendSFX;
import PrismaMod.orbs.IllyaPoisonOrb;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_TWO;

public class HassanGiftAction extends AbstractCardAction {

    public boolean upgraded;
    public float delay;
    public boolean hit;
    public HassanGiftAction(AbstractPlayer p, boolean upgraded){
        this.p = p;
        this.handler = getHandler(p);
        this.target = p;
        this.sfx = new DefendSFX(p);
        this.delay = this.sfx.getStallTimer();
        this.duration = this.sfx.duration;
        AbstractDungeon.effectsQueue.add(sfx);
        this.hit = false;
        this.upgraded = upgraded;
        handler.setAnimation(SKILL_TWO);
        //handler.setAnimtion(CHANNEL_ANIM);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaPoisonOrb(), true));
            if (upgraded) {
                AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaPoisonOrb(), true));
            }
            this.hit = true;
        }
        this.tickDuration();
    }
}
