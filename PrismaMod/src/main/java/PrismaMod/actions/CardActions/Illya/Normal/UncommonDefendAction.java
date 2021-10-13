package PrismaMod.actions.CardActions.Illya.Normal;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX.DefendSFX;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_ONE;

public class UncommonDefendAction extends AbstractCardAction {

    private int block;
    public float delay;
    public boolean hit;
    public int numPlate;
    public UncommonDefendAction(AbstractPlayer p, int block, int numPlate){
        this.target = p;
        this.handler = getHandler(p);
        this.sfx = new DefendSFX(p);
        AbstractDungeon.effectsQueue.add(sfx);
        this.block = block;
        this.duration = this.sfx.duration;
        //handler.setAnimtion(DEFEND_ANIM);
        handler.setAnimation(SKILL_ONE);
        this.delay = this.sfx.getStallTimer();
        this.numPlate = numPlate;
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            //logger.info("HIT");
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(target,target,block));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, target, new PlatedArmorPower(target, numPlate)));
            this.hit = true;
        }

        //handler.animationUpdate();
        this.tickDuration();
    }
}
