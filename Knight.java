package AdventureModel;

import java.util.List;

public class Knight extends BasicMonster {

    public Knight(int health, int attack, int defense, List<SpecialAbility> specialAbilities) {
        super(health, attack, defense, specialAbilities);
    }

    @Override
    public void interactWithPlayer(Player player) {
        // Specific interaction logic for Knight
    }

    // Knight-specific methods...
}
