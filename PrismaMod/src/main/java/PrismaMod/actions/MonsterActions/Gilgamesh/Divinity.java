package PrismaMod.actions.MonsterActions.Gilgamesh;

import PrismaMod.actions.MonsterActions.AbstractMonsterAction;
import PrismaMod.actions.MonsterActions.Gilgamesh.sfx.DivinitySFX;
import PrismaMod.monsters.AbstractPrismaMonster;
import PrismaMod.monsters.bossMonsters.Gilgamesh;
import PrismaMod.powers.monsters.DivinityPower;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class Divinity extends AbstractMonsterAction {

    public DamageInfo attack;
    public Divinity(Gilgamesh m, AbstractCreature target){
        this.m = m;
        this.handler = getHandler(m);
        this.target = target;
        this.sfx = new DivinitySFX(m);
        AbstractDungeon.effectsQueue.add(sfx);
        this.duration = this.sfx.duration;
        this.delay = this.sfx.getStallTimer();// - this.sfx.getEndStall()/4F;
        this.hit = false;
        handler.setAnimation(AbstractPrismaMonster.MonsterAnimationEnums.ATTACK);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.target, this.target, new DivinityPower(this.target, this.target, 1)));
            this.hit = true;
        }
        this.tickDuration();
    }
}
