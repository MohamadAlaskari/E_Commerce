package domain;

import java.util.List;
import domain.exceptions.AnzahlIsNichtDefiniertException;
import domain.exceptions.ArtikelExistiertNichtException;
import domain.exceptions.KundeIDistbenutztException;
import domain.exceptions.MitarbeiterIDIstBenutztException;
import domain.exceptions.NichtGenugArtikelVorhandenException;
import domain.exceptions.NutzernameOderPasswortFalschException;
import domain.exceptions.VerlaufLeerException;
import domain.exceptions.WarenkorbLeerException;
import entities.Adresse;
import entities.Artikel;
import entities.Bestellung;
import entities.Kunde;
import entities.Mitarbeiter;
import entities.Nutzer;
import entities.Rechnung;
import entities.Verlauf;
import entities.Warenkorb;

public class E_Shop {

	private ArtikelVerwaltung artikelVW;
	private WarenkorbVerwaltung warenKorbVW;
	private BestellungVerwaltung bestellVW;
	private KundeVerwaltung kundeVW;
	private MitarbeiterVerwaltung mitarbeiterVW;
	private RechnungsVerwaltung rechnungVW;
	private VerlaufsVerwaltung verlaufVW;

	public E_Shop() {
		artikelVW = new ArtikelVerwaltung();
		warenKorbVW = new WarenkorbVerwaltung();
		bestellVW = new BestellungVerwaltung();
		kundeVW = new KundeVerwaltung();
		mitarbeiterVW = new MitarbeiterVerwaltung();
		rechnungVW = new RechnungsVerwaltung();
		verlaufVW = new VerlaufsVerwaltung();
	}

	// Artikel Methoden
	public List<Artikel> gibAlleArtikeln() {
		return artikelVW.getArtikelListe();
	}

	public Artikel sucheNachName(String name) throws ArtikelExistiertNichtException {
		return artikelVW.sucheArtikel(name);
	}

	public Artikel fuegeArtikelEin(String name, String beschreibung, int bestand, double preis, int anzahl)
			throws AnzahlIsNichtDefiniertException {

		Artikel artikel = new Artikel(name, beschreibung, bestand, preis);
		artikelVW.fugeArtikelEin(artikel, anzahl);
		return artikel;
	}

	public Artikel fuegeArtikelEin(String name, String beschreibung, int bestand, double preis)
			throws AnzahlIsNichtDefiniertException {

		Artikel artikel = new Artikel(name, beschreibung, bestand, preis);
		artikelVW.fugeArtikelEin(artikel);
		return artikel;
	}

	public Artikel loescheArtikel(int id, String name, String beschreibung, int bestand, double preis,
			boolean verf�gbarkeit) throws ArtikelExistiertNichtException {
		Artikel artikel = new Artikel(id, name, beschreibung, bestand, preis, verf�gbarkeit);
		artikelVW.artikelloeschen(artikel);
		return artikel;

	}

	public Artikel erhoeheArtikelBestand(String name, int anzahl) throws ArtikelExistiertNichtException {
		Artikel artikel = artikelVW.bestandErhoehen(name, anzahl);
		return artikel;
	}

	public Artikel senkenArtikelBestand(String name, int anzahl) throws ArtikelExistiertNichtException {
		Artikel artikel = artikelVW.bestandSenken(name, anzahl);
		return artikel;
	}

	public void gibArtikelnlisteAus(List<Artikel> artikelListe) {
		if (artikelListe.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			for (Artikel artikel : artikelListe) {
				System.out.println(artikel);
			}
		}
	}

	// Kunde Methoden
	public void kundenRegistrieren(String name, String vorname, String nutzerNr, String passwort, String strasse,
			String hNr, String plz, String ort, String land) throws KundeIDistbenutztException {
		Adresse adresse = new Adresse(strasse, hNr, plz, ort, land);
		Kunde kunde = new Kunde(name, vorname, nutzerNr, passwort, adresse);
		kundeVW.kundeRegistieren(kunde);

	}

	public Kunde kundenEinloggen(String nutzerName, String passwort) throws NutzernameOderPasswortFalschException {
		return kundeVW.kundeEinloggen(nutzerName, passwort);

	}

	public List<Bestellung> GibAlleMeineBestellungen(Kunde kunde) {
		return kundeVW.getMeineBestellungen(kunde);
	}

	public void loggeKundeAus(Kunde kunde) {
		kundeVW.kundeAusloggen(kunde);
	}

	public List<Kunde> gibAlleKunden() {
		return kundeVW.getList_Kunde();
	}
	// Mitarbeiter Methoden

	public void mitarbeiterEinf�gen(String name, String vorName, String nutzerName, String passwort)
			throws MitarbeiterIDIstBenutztException {

		Mitarbeiter mitarbeiter = new Mitarbeiter(name, vorName, nutzerName, passwort);
		mitarbeiterVW.fuegeMitarbeiterEin(mitarbeiter);

	}

	public Mitarbeiter mitarbeiterEinloggen(String nutzerName, String passwort)
			throws NutzernameOderPasswortFalschException {
		return mitarbeiterVW.mitarbeiterEinloggen(nutzerName, passwort);

	}

	public void regestiereNeueMitarbeiter(String name, String vorName, String nutzerName, String passwort)
			throws MitarbeiterIDIstBenutztException {

		mitarbeiterVW.neueMitarbeiterRegistieren(name, vorName, nutzerName, passwort);

	}

	public void loggeMitarbeiterAus(Mitarbeiter mitarbeiter) {
		mitarbeiterVW.mitarbeiterAusloggen(mitarbeiter);
	}

	public List<Mitarbeiter> gibAlleMitarbeiter() {
		return mitarbeiterVW.getList_Mitarbeiter();
	}
	// Rechnung Methoden

	public Rechnung erstelleRechnung(Bestellung bestl) {

		return rechnungVW.erstelleRechnung(bestl);
	}

	public List<Rechnung> GinAlleRechnungen() {
		return rechnungVW.getRechnungenList();
	}

	// Warenkorb

	public void fuegeArtikelInkorbEin(Kunde kunde, Artikel art, int anzahl) throws NichtGenugArtikelVorhandenException {
		warenKorbVW.fuegeArtikelInKorbEin(kunde, art, anzahl);
	}

	public void entferneArtikelVomWarenkorb(Kunde kunde, Artikel art, int anzahl)
			throws AnzahlIsNichtDefiniertException {
		warenKorbVW.entferneArtikelKorbListe(kunde, art, anzahl);
	}

	public void loescheArtikelVomWarenkorb(Kunde kunde, Artikel art) {
		warenKorbVW.loescheArtikeVomKorb(kunde, art);
	}

	public void leereWarenkorb(Kunde kunde) {
		warenKorbVW.leereWarenKorb(kunde);
	}

	public Warenkorb getKundenWarenkorb(Kunde kunde) {
		return warenKorbVW.getWarenkorb(kunde);
	}

	// Bestellung
	public Bestellung bestellen(Kunde kunde) throws WarenkorbLeerException {
		return bestellVW.bestellen(kunde);
	}

	public List<Bestellung> getBestellungList() {
		return bestellVW.getBestellungList();
	}

	// Verlauf
	public void addVerlauf(String aktion, Nutzer nutzer, Artikel artikel) {
		verlaufVW.addVerlauf(aktion, nutzer, artikel);

	}

	public List<Verlauf> gibVerlauflistaus() throws VerlaufLeerException {
		return verlaufVW.getVerlauflListe();
	}

}
