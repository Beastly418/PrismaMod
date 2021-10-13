package PrismaMod.actions.CardActions.Illya.Misc;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.MiscSFX.AliceSFX;
import PrismaMod.relics.PrismaModAliceRelic;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_ONE;
import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_TWO;

public class AliceAction extends AbstractCardAction {

    private AbstractPlayer p;
    private boolean getRelic;
    public boolean hit;
    public float delay;
    public int heal;

    public AliceAction(AbstractPlayer p, boolean getRelic, int heal){
        this.getRelic = getRelic;
        this.actionType = ActionType.WAIT;
        this.sfx = new AliceSFX(p);
        this.duration = this.sfx.duration;
        this.delay = this.sfx.getStallTimer();// - this.sfx.getEndStall()/4F;
        this.hit = false;
        this.heal = heal;
        this.target = p;
        this.handler = getHandler(p);
        AbstractDungeon.effectsQueue.add(this.sfx);
        if(!getRelic){
            //Only heals
            handler.setAnimation(SKILL_ONE);
        } else if (getRelic) {
            //Gets the relic
            handler.setAnimation(SKILL_TWO);

        }
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if (getRelic && !hit && delay <= 0) {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new PrismaModAliceRelic());
            hit = true;
        } else if (!getRelic && !hit && delay <= 0) {
            AbstractDungeon.actionManager.addToTop(new HealAction(target, target, this.heal));
            hit = true;
        }

        //this.isDone = true;
        this.tickDuration();
    }
}
