## DeluxeDurability 1.0.0 by xPlugins

Un plugin potente y ligero para Minecraft que te permite tomar el control total sobre la durabilidad de los ítems en tu servidor. Olvídate de los límites de vanilla y crea armas, herramientas y armaduras con miles de puntos de durabilidad, comportamientos únicos y lore dinámico.

## ✨ Características Principales
Durabilidad 100% Personalizable: Asigna cualquier valor de durabilidad a cualquier ítem, desde 1 hasta millones.

Barra de Durabilidad Visual: La barra de durabilidad del ítem se actualiza visualmente en tiempo real para reflejar su vida útil personalizada.

Lore Dinámico: Añade un lore a tus ítems que muestra la durabilidad actual y máxima usando placeholders ({current} y {max}).

Control Total sobre la Ruptura: Decide si un ítem se romperá al llegar a 0 de durabilidad o si se volverá simplemente inutilizable.

Desactivar Reparación: Evita que ciertos ítems puedan ser reparados en un yunque.

Equipamiento Rápido: Configura armaduras para que se equipen automáticamente en su ranura correcta con solo hacer clic derecho.

Sistema de Comandos Sencillo: Incluye comandos para obtener ítems, recargar la configuración y ver la ayuda del plugin.

Alta Configuración: Toda la lógica de los ítems, así como los mensajes del plugin, se gestionan desde un único y claro archivo config.yml.

## 🔧 Configuración de un Ítem
Crear un ítem es tan fácil como añadir una nueva sección en el archivo config.yml. ¡Aquí tienes un ejemplo!
```yaml
#       ___     _                   ___                 _     _ _ _ _
#     /   \___| |_   ___  _____   /   \_   _ _ __ __ _| |__ (_) (_) |_ _   _
#    / /\ / _ \ | | | \ \/ / _ \ / /\ / | | | '__/ _` | '_ \| | | | __| | | |   PLugin by
#   / /_//  __/ | |_| |>  <  __// /_//| |_| | | | (_| | |_) | | | | |_| |_| |  xPlugins for
#  /___,' \___|_|\__,_/_/\_\___/___,'  \__,_|_|  \__,_|_.__/|_|_|_|\__|\__, |  Vertix Network
#                                                                      |___/
#                    Discord: https://discord.gg/vDcAGTGF3h
#                        xPlugins 2025 x 777 Studios

# Mensajes usados en comandos y eventos.
messages:
  prefix: "&8[&eDeluxeDurability&8] &r"
  reload: "&aLa configuración se ha recargado exitosamente."
  no-permission: "&cNo tienes permiso para ejecutar este comando."
  player-only: "&cEste comando solo puede ser ejecutado por un jugador."
  item-not-found: "&cEl ítem con la ID '{id}' no fue encontrado."
  item-given: "&aHas recibido el ítem '{id}'."
  help:
    - "&e&lAyuda de DeluxeDurability"
    - "&e/dd give <item_id> &7- Te entrega un ítem personalizado."
    - "&e/dd reload &7- Recarga la configuración del plugin."
    - "&e/dd help &7- Muestra este mensaje de ayuda."

# Define tus ítems personalizados aquí.
items:
  CABEZA_ARANA_HALLOWEEN:
    display-name: "&cCasco de Araña de Halloween"
    material: PAPER
    custom-model-data: 10079
    # Con esta opción, el ítem se equipará en la cabeza al hacer clic derecho.
    # Valores posibles: HELMET, CHESTPLATE, LEGGINGS, BOOTS
    equip-slot: HELMET
    max-durability: 407
    will-break: true
    disable-repairing: false
    lore:
      - "&7Un casco espeluznante de otra dimensión."
      - "&7Durabilidad: &f{current}&8/&f{max}"

  PECHERA_ARANA_HALLOWEEN:
    display-name: "&cPechera de Araña de Halloween"
    material: LEATHER_CHESTPLATE
    custom-model-data: 10006
    # Se equipará en el pecho al hacer clic derecho.
    equip-slot: CHESTPLATE
    max-durability: 592
    will-break: true
    disable-repairing: false
    dye-color: "156, 54, 33" # Color en formato R, G, B
    lore:
      - "&7Una pechera espeluznante de otra dimensión."
      - "&7Durabilidad: &f{current}&8/&f{max}"

  VARITA_DIAMANTE:
    display-name: "&bVarita de Diamante"
    material: DIAMOND_HOE
    custom-model-data: 1
    # Al no tener 'equip-slot', este ítem no se auto-equipará.
    max-durability: 10000
    will-break: false # Este ítem no se romperá al llegar a 0 de durabilidad.
    disable-repairing: true
    lore:
      - "&7Una varita poderosa con durabilidad inmensa."
      - "&7Se siente mágica."
      - "&7Durabilidad: &f{current}&8/&f{max}"

  BOTAS_DE_SANGRE:
    display-name: "&4Botas de Sangre"
    material: NETHERITE_BOOTS
    custom-model-data: 101
    equip-slot: BOOTS
    max-durability: 2500
    will-break: true
    disable-repairing: false
    lore:
      - "&cForjadas en las profundidades del nether."
      - "&7Durabilidad: &f{current}&8/&f{max}"

```

## 🎮 Comandos y Permisos
El comando principal es /deluxedurability con su alias /dd.

Comando	Descripción	Permiso
`/dd help	Muestra la lista de comandos disponibles.	(Ninguno)
/dd give <ID_del_item>	Entrega un ítem personalizado de la config.	deluxedurability.give
/dd reload	Recarga el archivo config.yml sin reiniciar.	deluxedurability.reload`

## 🚀 Instalación
Descarga la última versión del plugin desde la sección de Releases.

Coloca el archivo `.jar` descargado en la carpeta plugins de tu servidor.

Reinicia o enciende tu servidor.

¡Configura tus ítems en el archivo plugins/DeluxeDurability/config.yml y disfruta!

- Este plugin ha sido diseñado y desarrollado por xPlugins x WillfryDev.
