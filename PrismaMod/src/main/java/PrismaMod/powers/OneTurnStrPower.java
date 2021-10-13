package PrismaMod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class OneTurnStrPower extends StrengthPower {
    public OneTurnStrPower(AbstractPlayer p, int amt) {
        super(p, amt);
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, StrengthPower.POWER_ID, this.amount));
    }
}