package PrismaMod.actions.CardActions.Illya.Poison;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.unique.BouncingFlaskAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_ONE;

public class BlindPoisonTossAction extends AbstractCardAction {

    public int poison;
    public int numBounces;
    public float delay;
    public boolean hit;
    public BlindPoisonTossAction(AbstractPlayer p, int poison, int numBounces){
        this.p = p;
        this.handler = getHandler(p);
        this.target = p;
        this.duration = 0;//this.sfx.duration;
        this.delay = 0;//this.sfx.getStallTimer();// - this.sfx.getEndStall()/4F;
        this.hit = false;
        this.poison = poison;
        this.numBounces = numBounces;
        handler.setAnimation(SKILL_ONE);
        //handler.setAnimtion(CHANNEL_ANIM);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng);
            AbstractDungeon.actionManager.addToBottom(new BouncingFlaskAction(randomMonster, this.poison, this.numBounces));
            this.hit = true;
        }
        this.tickDuration();
    }
}
