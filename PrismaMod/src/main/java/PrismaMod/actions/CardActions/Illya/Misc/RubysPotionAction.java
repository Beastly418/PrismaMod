package PrismaMod.actions.CardActions.Illya.Misc;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX.DefendSFX;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_THREE;

public class RubysPotionAction extends AbstractCardAction {

    public float delay;
    public boolean hit;
    public int heal;

    public RubysPotionAction(AbstractPlayer p, int heal){
        this.p = p;
        this.handler = getHandler(p);
        this.sfx = new DefendSFX(p);
        this.delay = this.sfx.getStallTimer();
        this.duration = this.sfx.duration;
        AbstractDungeon.effectsQueue.add(sfx);
        this.target = p;
        this.hit = false;
        this.heal = heal;
        handler.setAnimation(SKILL_THREE);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            AbstractDungeon.actionManager.addToTop(new HealAction(p, p, this.heal));
            this.hit = true;
        }
        this.tickDuration();
    }
}
