function setValueIfExists(e, t) {
    const n = document.getElementById(e);
    n && null !== t && (n.value = t);
}
document.addEventListener("DOMContentLoaded", () => {
    const e = new URLSearchParams(window.location.search),
        t = (e.get("action"), e.get("nom")),
        n = e.get("prenom"),
        s = e.get("email");
    setValueIfExists("nom", t), setValueIfExists("prenom", n), setValueIfExists("email", s);
});
