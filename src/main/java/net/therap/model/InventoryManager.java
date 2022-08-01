package net.therap.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author raian.rahman
 * @since 8/1/22
 */
@Entity
@Table(name = "inventory_manager", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_name"})})
public class InventoryManager extends Person {
}
