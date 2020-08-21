
package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "contact",
    "location",
    "categories",
    "verified",
    "stats",
    "beenHere",
    "hereNow",
    "referralId",
    "venueChains",
    "hasPerk"
})
public class Venue {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("contact")
    private Contact contact;
    @JsonProperty("location")
    private Location location;
    @JsonProperty("categories")
    private List<Category> categories = null;
    @JsonProperty("verified")
    private Boolean verified;
    @JsonProperty("stats")
    private Stats stats;
    @JsonProperty("beenHere")
    private BeenHere beenHere;
    @JsonProperty("hereNow")
    private HereNow hereNow;
    @JsonProperty("referralId")
    private String referralId;
    @JsonProperty("venueChains")
    private List<Object> venueChains = null;
    @JsonProperty("hasPerk")
    private Boolean hasPerk;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Venue() {
    }

    /**
     * 
     * @param hasPerk
     * @param hereNow
     * @param stats
     * @param referralId
     * @param contact
     * @param venueChains
     * @param name
     * @param verified
     * @param location
     * @param id
     * @param categories
     * @param beenHere
     */
    public Venue(String id, String name, Contact contact, Location location, List<Category> categories, Boolean verified, Stats stats, BeenHere beenHere, HereNow hereNow, String referralId, List<Object> venueChains, Boolean hasPerk) {
        super();
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.location = location;
        this.categories = categories;
        this.verified = verified;
        this.stats = stats;
        this.beenHere = beenHere;
        this.hereNow = hereNow;
        this.referralId = referralId;
        this.venueChains = venueChains;
        this.hasPerk = hasPerk;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("contact")
    public Contact getContact() {
        return contact;
    }

    @JsonProperty("contact")
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @JsonProperty("location")
    public Location getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(Location location) {
        this.location = location;
    }

    @JsonProperty("categories")
    public List<Category> getCategories() {
        return categories;
    }

    @JsonProperty("categories")
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @JsonProperty("verified")
    public Boolean getVerified() {
        return verified;
    }

    @JsonProperty("verified")
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    @JsonProperty("stats")
    public Stats getStats() {
        return stats;
    }

    @JsonProperty("stats")
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    @JsonProperty("beenHere")
    public BeenHere getBeenHere() {
        return beenHere;
    }

    @JsonProperty("beenHere")
    public void setBeenHere(BeenHere beenHere) {
        this.beenHere = beenHere;
    }

    @JsonProperty("hereNow")
    public HereNow getHereNow() {
        return hereNow;
    }

    @JsonProperty("hereNow")
    public void setHereNow(HereNow hereNow) {
        this.hereNow = hereNow;
    }

    @JsonProperty("referralId")
    public String getReferralId() {
        return referralId;
    }

    @JsonProperty("referralId")
    public void setReferralId(String referralId) {
        this.referralId = referralId;
    }

    @JsonProperty("venueChains")
    public List<Object> getVenueChains() {
        return venueChains;
    }

    @JsonProperty("venueChains")
    public void setVenueChains(List<Object> venueChains) {
        this.venueChains = venueChains;
    }

    @JsonProperty("hasPerk")
    public Boolean getHasPerk() {
        return hasPerk;
    }

    @JsonProperty("hasPerk")
    public void setHasPerk(Boolean hasPerk) {
        this.hasPerk = hasPerk;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
