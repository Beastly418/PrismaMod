package PrismaMod.actions.MonsterActions.Gilgamesh;

import PrismaMod.actions.MonsterActions.AbstractMonsterAction;
import PrismaMod.actions.MonsterActions.Rider.sfx.BasicAttackSFX;
import PrismaMod.monsters.AbstractPrismaMonster;
import PrismaMod.monsters.bossMonsters.Gilgamesh;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BasicAttack extends AbstractMonsterAction {

    public DamageInfo attack;
    public BasicAttack(Gilgamesh m, AbstractCreature target, DamageInfo attack){
        this.m = m;
        this.handler = getHandler(m);
        this.target = target;
        this.sfx = new BasicAttackSFX(m, (AbstractPlayer)target);
        AbstractDungeon.effectsQueue.add(sfx);
        this.duration = this.sfx.duration;
        this.delay = this.sfx.getStallTimer();// - this.sfx.getEndStall()/4F;
        this.hit = false;
        this.attack = attack;
        handler.setAnimation(AbstractPrismaMonster.MonsterAnimationEnums.ATTACK);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            this.target.damage(attack);
            this.hit = true;
        }
        this.tickDuration();
    }
}
