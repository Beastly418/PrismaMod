package PrismaMod.cards.ChloeCards;

import PrismaMod.cards.AbstractDynamicCard;
import basemod.BaseMod;
import basemod.abstracts.CustomCard;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

//Not really needed anymore since I changed how everything is going to work but it still is needed for the one card that its used on :)

public abstract class AbstractTraceCard extends CustomCard {

    public AbstractTraceCard(String id,
                               final String img,
                               final int cost,
                               final CardType type,
                               final CardColor color,
                               final CardRarity rarity,
                               final CardTarget target,
                               String description){
        super(id, languagePack.getCardStrings(id).NAME, img, cost, description, type, color, rarity, target);
    }
    public AbstractTraceCard(String id,
                             final String img,
                             final int cost,
                             final CardType type,
                             final CardColor color,
                             final CardRarity rarity,
                             final CardTarget target){
        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);
    }


}
