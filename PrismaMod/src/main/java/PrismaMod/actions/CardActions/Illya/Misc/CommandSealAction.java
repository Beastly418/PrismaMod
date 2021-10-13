package PrismaMod.actions.CardActions.Illya.Misc;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.MiscSFX.CommandSealSFX;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_ONE;

public class CommandSealAction extends AbstractCardAction {

    public float delay;
    public boolean hit;
    public int heal;

    public CommandSealAction(AbstractPlayer p, int heal){
        this.p = p;
        this.handler = getHandler(p);
        this.sfx = new CommandSealSFX(p);
        AbstractDungeon.effectsQueue.add(sfx);
        this.target = p;
        this.duration = this.sfx.duration;
        this.delay = this.sfx.getStallTimer();// - this.sfx.getEndStall()/4F;
        this.hit = false;
        this.heal = heal;
        //handler.setAnimtion(CHANNEL_ANIM);
        handler.setAnimation(SKILL_ONE);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1)));
            AbstractDungeon.actionManager.addToTop(new HealAction(p, p, this.heal));
            this.hit = true;
        }
        this.tickDuration();
    }
}
