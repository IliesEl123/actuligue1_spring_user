package fr.isika.iliesel.projet4back.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "matchs")
public class Match {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long matchId;
	private String matchRef;

	@OneToOne
	private User user;

}
