# osw
Openspot Widget (OSW)

OSW is a little to remote control tool for SharkRF Openspot (c).
It is esspecially useful, if you use a DMR radio, which has no promiscious mode.

The basic idea is to have a small window on your computer, which enables you to switch between TalkGroups (TGs) and reroute TGs
to TG9. By doing so, you can leave your DMR Radio on TG9 and work the TG you like.

Basically all this is also possible from the OpenSpot WebInterface, but we consider this more convinient.
E.g. OSW stays logged in in contrast to the Web interface, where you have to re-login after a specific time.

If you dont want to compile it by yourself, just use the osw-1.0-SNAPSHOT.
You have to make sure that the osw_connection.config and the calls.config is in the same directory as the jar.
These are json files, which hold the basic informations. You can look into with a text editor, but usually there is no reason to do so.

I hope it is useful to you
Peter (dk3rn)

mail at dk3rn.de
