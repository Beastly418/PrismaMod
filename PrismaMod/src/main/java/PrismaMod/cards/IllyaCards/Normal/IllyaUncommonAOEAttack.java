package PrismaMod.cards.IllyaCards.Normal;

import PrismaMod.actions.CardActions.Illya.Normal.AOEUncommonAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Illya;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import PrismaMod.DefaultMod;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.WeakPower;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaUncommonAOEAttack extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(IllyaUncommonAOEAttack.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/UncommonAOEAttack.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 1;
    private static final int DPS = 6;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int NUM_WEAK = 2;

    // /STAT DECLARATION/

    public IllyaUncommonAOEAttack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        // Aside from baseDamage/MagicNumber/Block there's also a few more.
        // Just type this.base and let intelliJ auto complete for you, or, go read up AbstractCard

        baseDamage = damage = DPS;
        this.magicNumber = this.baseMagicNumber = NUM_WEAK;
        this.aoe = true;
        //this.tags.add(BaseModCardTags.BASIC_STRIKE); //Tag your strike, defend and form (Wraith form, Demon form, Echo form, etc.) cards so that they function correctly.
        //this.tags.add(CardTags.STRIKE);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new AOEUncommonAction(p, damage, damageType, NUM_WEAK));
        } else {
            AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, new int[]{damage, damage, damage, damage}, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HEAVY));
            MonsterGroup enemies = AbstractDungeon.getMonsters();
            for (AbstractMonster mon : enemies.monsters) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mon, mon, new WeakPower(mon, NUM_WEAK, true)));
            }
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
