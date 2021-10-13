package PrismaMod.actions.CardActions.Illya.Poison;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX.DefendSFX;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_ONE;

public class BoysLoveAction extends AbstractCardAction {

    public int poison;
    public float delay;
    public boolean hit;
    public BoysLoveAction(AbstractPlayer p, int poison){
        this.p = p;
        this.handler = getHandler(p);
        this.target = p;
        this.sfx = new DefendSFX(p);
        this.delay = this.sfx.getStallTimer();
        this.duration = this.sfx.duration;
        AbstractDungeon.effectsQueue.add(sfx);
        this.hit = false;
        this.poison = poison;
        handler.setAnimation(SKILL_ONE);
        //handler.setAnimtion(CHANNEL_ANIM);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            MonsterGroup mon = AbstractDungeon.getMonsters();
            for (AbstractMonster monst : mon.monsters) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(monst, monst, new PoisonPower(monst, monst, poison)));//,1, AbstractGameAction.AttackEffect.NONE));
            }
            this.hit = true;
        }
        this.tickDuration();
    }
}
