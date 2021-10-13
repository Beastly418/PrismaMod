package PrismaMod.actions.CardActions.Illya.Poison;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX.DefendSFX;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_TWO;

public class RussianRouletteAction extends AbstractCardAction {

    public int poison;
    public float delay;
    public boolean hit;
    public RussianRouletteAction(AbstractPlayer p, int poison){
        this.p = p;
        this.handler = getHandler(p);
        this.target = p;
        this.sfx = new DefendSFX(p);
        this.delay = this.sfx.getStallTimer();
        this.duration = this.sfx.duration;
        AbstractDungeon.effectsQueue.add(sfx);
        this.hit = false;
        this.poison = poison;
        handler.setAnimation(SKILL_TWO);
        //handler.setAnimtion(CHANNEL_ANIM);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            AbstractMonster mon = AbstractDungeon.getRandomMonster();
            AbstractPlayer player = AbstractDungeon.player;
            int total = 6;
            double rand = (Math.random() * total);
            if (rand < 1) { //1/6 chance to hit self
                //Hit player
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(player, player, new PoisonPower(player, player, poison)));//,1, AbstractGameAction.AttackEffect.NONE));
            } else {
                //Hit selected minion
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mon, mon, new PoisonPower(mon, mon, poison)));//,1, AbstractGameAction.AttackEffect.NONE));
            }
            this.hit = true;
        }
        this.tickDuration();
    }
}
