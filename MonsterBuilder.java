package AdventureModel;

import java.util.List;

public class MonsterBuilder {
    private int health;
    private int attack;
    private int defense;
    private List<SpecialAbility> specialAbilities;

    public MonsterBuilder setHealth(int health) {
        this.health = health;
        return this;
    }

    // Other setters...

    public BasicMonster buildBasicMonster() {
        return new BasicMonster(health, attack, defense, specialAbilities);
    }

    // Methods for building other types of monsters...
}
