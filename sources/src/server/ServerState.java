package server;

/**
 * Liste des états du serveur. Utilisé pour implémenter la "state machine"
 * du serveur. Les états disponibles sont :
 * 
 * - CONNECT : En attente de connexion des joueurs
 * - BET : En attente des mises des joueurs
 * - DISTRIBUTE_SECRET_HAND : En attente des nouvelles cartes du gagnant de la mise
 * - GAME : Déroulement d'une partie
 * - END : Fin de la partie
 * - DISCONNECT : En attente de la déconnexion des joueurs
 * 
 * @author Gregory Eric Sanderson <gzou2000@gmail.com>
 *
 */
public enum ServerState {
	
	CONNECT,
	BET,
	DISTRIBUTE_SECRET_HAND,
	GAME,
	END,
	DISCONNECT,

}
