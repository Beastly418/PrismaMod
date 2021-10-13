package PrismaMod.cards.ChloeCards.TraceCards;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Chloe.TraceAction;
import PrismaMod.cards.ChloeCards.AbstractTraceCard;
import PrismaMod.characters.Chloe;
import PrismaMod.characters.Illya;
import PrismaMod.relics.PrismaModTraceOnRelic;
import PrismaMod.relics.PrismaModTraceOnUpgradeRelic;
import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static PrismaMod.DefaultMod.logger;
import static PrismaMod.DefaultMod.makeCardPath;
import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public class ChloeCreateableTraceCard extends AbstractTraceCard implements CustomSavable<String> {

    public static final String ID = DefaultMod.makeID(ChloeCreateableTraceCard.class.getSimpleName());
    public static final String IMG = makeCardPath("Chloe/TraceOn.png"); //Default
    public static final String IMGATK = makeCardPath("Chloe/TraceOnAttack.png"); //Default
    public static final String IMGSKL = makeCardPath("Chloe/TraceOnSkill.png"); //Default
    public static final String IMGPWR = makeCardPath("Chloe/TraceOnPower.png"); //Default


    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardRarity RARITY = CardRarity.SPECIAL;
    public static final CardColor COLOR = Chloe.Enums.COLOR_CHLOE_RED;

    public ArrayList<AbstractCard> cards = new ArrayList<>(20);
    public CardColor fakeColor;
    public CardType fakeType;
    public CardRarity fakeRarity;

    private static String[] desc = languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;
    private static String descUp = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;

    private static final int COST = 0;

    //THE UPGRADE DESCRIPTION IS FOR THE ACTION
    //DEFAULT CONSTRUCTOR IS ONLY USED TO SHOW IT IN THE CARD LIBRARY
    public ChloeCreateableTraceCard(){
        this(COLOR, CardRarity.UNCOMMON, CardType.ATTACK, PrismaModTraceOnRelic.getCards(new PrismaModTraceOnRelic.TraceObject(COLOR, CardRarity.UNCOMMON, CardType.ATTACK)), false);
    }

    public ChloeCreateableTraceCard(CardColor color, CardRarity rarity, CardType type, ArrayList<AbstractCard> cards, boolean upgrade){
        super(ID, type == CardType.ATTACK ? IMGATK : type == CardType.SKILL ? IMGSKL : type == CardType.POWER ? IMGPWR : IMG
                , COST, type, color, RARITY, TARGET, descriptionMaker(color, rarity, type, upgrade));//desc[0] + color.toString() + desc[1] + rarity.toString() + desc[2] + type.toString() + desc[3]);
        this.fakeColor = color;
        this.fakeType = type;
        this.fakeRarity = rarity;
        this.exhaust = true;
        this.cards = cards;
        if(type == CardType.ATTACK) {

        } else if(type == CardType.POWER) {

        } else if(type == CardType.SKILL) {

        }
        //setBackgroundTexture(this.fakeColor);
        //setOrbTexture(this.fakeColor);
    }



    private static String descriptionMaker(CardColor color, CardRarity rarity, CardType type, boolean upgraded){
        String output = "";
        String colorString = color.toString().toLowerCase().replaceAll("color_", "");
        colorString = colorString.replaceAll("_", " ");
        String rarityString = rarity.toString().toLowerCase();
        String typeString = type.toString().toLowerCase();
        if(!upgraded) {
            output = desc[0] + desc[1] + colorString + desc[2] + rarityString + desc[3] + typeString + desc[4];
        } else {
            output = desc[0] + descUp + desc[1] + colorString + desc[2] + rarityString + desc[3] + typeString + desc[4];
        }
        return output;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.cards.size() < 1) {
            if(!upgraded){
                this.cards = PrismaModTraceOnRelic.getCards(new PrismaModTraceOnRelic.TraceObject(this.color, this.rarity, this.fakeType));
            } else {
                this.cards = PrismaModTraceOnUpgradeRelic.getCards(new PrismaModTraceOnRelic.TraceObject(this.color, this.rarity, this.fakeType));
            }
        }
        AbstractDungeon.actionManager.addToTop(new TraceAction(p, this.cards));//this.fakeColor, this.fakeRarity, this.fakeType));
    }

    @Override
    public void upgrade() {
        if(!upgraded){
            upgradeName();
            rawDescription = descriptionMaker(this.color, this.fakeRarity, this.type, true);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ChloeCreateableTraceCard(this.color, this.fakeRarity, this.type, this.cards, this.upgraded);
    }




    @Override
    public String onSave() {
        registerSaveId();

        logger.debug(getLogPrefix("onSave") + " called");

        String holdingCell = "";
        for(AbstractCard c : this.cards) {
            holdingCell = holdingCell + c.cardID + "@";
            logger.debug(holdingCell);
        }
        return holdingCell;
    }

    @Override
    public void onLoad(String s) {
        if(s != null) {
            logger.debug(getLogPrefix("onLoad") + " Loading cards for card pool from save:");
            String[] ids = s.split("@");
            int cardIndex = 0;
            for(String id : ids) {
                logger.debug(ids);
                if(CardLibrary.isACard(id)) {
                    AbstractCard card = CardLibrary.getCard(id);
                    if(!this.upgraded) {
                        this.cards.add(card);
                    } else {
                        card.upgrade();
                        this.cards.add(card);
                    }
                    logger.debug(cardIndex++ + ") " + card.name + " [cardId: " + card.cardID + "]");
                } else {
                    logger.warn(cardIndex++ + ") NOT IN CARD LIBRARY [cardId: " + id + "]");
                }
            }
        }
    }

    private void registerSaveId() {
        logger.debug( this.getClass().getSimpleName() + "::registerSaveId");
        logger.debug( this.getClass().getSimpleName() + "::registerSaveId registering customSaveKey: " + getCustomSaveKey());
        BaseMod.addSaveField(this.getCustomSaveKey(), this);
    }

    public String getCustomSaveKey(){
        return DefaultMod.getModID() + this.getClass().getSimpleName();
    }

    private String getLogPrefix(String methodName) {
        return this.getClass().getSimpleName() + "." + methodName + " ::";
    }
}
