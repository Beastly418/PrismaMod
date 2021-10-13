package PrismaMod.actions.MonsterActions.Rider;

import PrismaMod.actions.MonsterActions.AbstractMonsterAction;
import PrismaMod.actions.MonsterActions.Rider.sfx.UnicornAttackSFX;
import PrismaMod.monsters.AbstractPrismaMonster;
import PrismaMod.monsters.bossMonsters.Rider;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class UnicornAttack extends AbstractMonsterAction {

    public DamageInfo attack;
    public int numAttacks;
    public UnicornAttack(Rider m, AbstractPlayer target, DamageInfo attack, int numAttacks){
        this.m = m;
        this.target = target;
        this.handler = getHandler(m);
        this.sfx = new UnicornAttackSFX(this.m, (AbstractPlayer)this.target);
        AbstractDungeon.effectsQueue.add(sfx);
        this.target = target;
        this.duration = this.sfx.duration;
        this.delay = this.sfx.getStallTimer();// - this.sfx.getEndStall()/4F;
        this.hit = false;
        this.attack = attack;
        this.numAttacks = numAttacks;
        handler.setAnimation(AbstractPrismaMonster.MonsterAnimationEnums.BIG_ATTACK);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            for(int i = 0; i < this.numAttacks; i++)
                this.target.damage(attack);
            this.hit = true;
        }
        this.tickDuration();
    }
}
