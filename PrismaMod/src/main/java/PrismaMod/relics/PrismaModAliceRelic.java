
package PrismaMod.relics;

import PrismaMod.DefaultMod;
import PrismaMod.characters.Illya;
import PrismaMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static PrismaMod.DefaultMod.makeRelicOutlinePath;
import static PrismaMod.DefaultMod.makeRelicPath;

public class PrismaModAliceRelic extends CustomRelic {

    public static final String ID = DefaultMod.makeID("AliceRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Shirou_Relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    private static final int NUMDRAW = 2;

    public PrismaModAliceRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        AbstractDungeon.player.draw(NUMDRAW);
        flash();
    }
}
