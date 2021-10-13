package PrismaMod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class OneTurnDexPower extends DexterityPower {
    public OneTurnDexPower(AbstractPlayer p, int amt) {
        super(p, amt);
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, DexterityPower.POWER_ID, this.amount));
    }
}
