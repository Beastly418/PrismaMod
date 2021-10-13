package PrismaMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;

public class InstallColorEnum {
    @SpireEnum
    public static AbstractPlayer.PlayerClass INSTALL;
    @SpireEnum(name = "Install") // These two HAVE to have the same absolutely identical name.
    public static AbstractCard.CardColor COLOR_INSTALL;
    @SpireEnum(name = "Install") @SuppressWarnings("unused")
    public static CardLibrary.LibraryType LIBRARY_COLOR;
}
