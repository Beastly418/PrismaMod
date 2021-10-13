package PrismaMod.cards.ChloeCards;

import PrismaMod.cards.AbstractDynamicCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public abstract class ChloeCarnivalCard extends AbstractDynamicCard {

    //This is used to figure out whether or not on the relic to do +2 base on the counter

    public ChloeCarnivalCard(String ID, String IMG, int COST, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }
}
