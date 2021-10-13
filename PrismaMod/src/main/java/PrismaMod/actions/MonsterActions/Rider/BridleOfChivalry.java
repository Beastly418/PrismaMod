package PrismaMod.actions.MonsterActions.Rider;

import PrismaMod.actions.MonsterActions.AbstractMonsterAction;
import PrismaMod.actions.MonsterActions.Rider.sfx.BridleOfChivalrySFX;
import PrismaMod.monsters.AbstractPrismaMonster;
import PrismaMod.monsters.bossMonsters.Rider;
import PrismaMod.powers.monsters.BridleOfChivalryPower;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BridleOfChivalry extends AbstractMonsterAction {

    public BridleOfChivalry(Rider m){
        this.m = m;
        this.handler = getHandler(m);
        this.sfx = new BridleOfChivalrySFX(m);
        AbstractDungeon.effectsQueue.add(sfx);
        this.duration = this.sfx.duration;
        this.delay = this.sfx.getStallTimer();// - this.sfx.getEndStall()/4F;
        this.hit = false;

        handler.setAnimation(AbstractPrismaMonster.MonsterAnimationEnums.BIG_CHANNEL);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.m, this.m, new BridleOfChivalryPower(this.m, this.m, 1)));
            this.hit = true;
        }
        this.tickDuration();
    }
}
