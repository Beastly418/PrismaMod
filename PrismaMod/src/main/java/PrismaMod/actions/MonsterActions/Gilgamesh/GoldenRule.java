package PrismaMod.actions.MonsterActions.Gilgamesh;

import PrismaMod.actions.MonsterActions.AbstractMonsterAction;
import PrismaMod.actions.MonsterActions.Gilgamesh.sfx.GoldenRuleSFX;
import PrismaMod.monsters.AbstractPrismaMonster;
import PrismaMod.monsters.bossMonsters.Gilgamesh;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GoldenRule extends AbstractMonsterAction {

    public int numBlock;
    public int numHeal;
    public GoldenRule(Gilgamesh m, int numBlock, int numHeal){
        this.m = m;
        this.handler = getHandler(m);
        this.target = target;
        this.sfx = new GoldenRuleSFX(m);
        AbstractDungeon.effectsQueue.add(sfx);
        this.duration = this.sfx.duration;
        this.delay = this.sfx.getStallTimer();// - this.sfx.getEndStall()/4F;
        this.hit = false;
        this.numBlock = numBlock;
        this.numHeal = numHeal;
        handler.setAnimation(AbstractPrismaMonster.MonsterAnimationEnums.ATTACK);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            this.m.heal(this.numHeal);
            this.m.addBlock(this.numBlock);
            this.hit = true;
        }
        this.tickDuration();
    }
}
