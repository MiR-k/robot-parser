{
                    events: [
                    <#if replays??>
                    <#list replays as event>
                    <#if event.getIvent()??>
                    <#assign ivent=event.getIvent()?map(it->it)>
                    <#list ivent as key, value>${key},${value}</#list>
                    </#if>
                        ${event}<#sep>,
                    </#list>
                    <#else>
                    <#if singleReplayEvent??>${singleReplayEvent},</#if>
                        { name: "change", attachToShadows: true, recurseFrames: true },
                        { name: "click", recurseFrames: true },
                        { name: "pointerdown", recurseFrames: true },
                        { name: "pointerup", recurseFrames: true },
                        { name: "hashchange", target: window },
                        { name: "focus", recurseFrames: true },
                        { name: "blur", recurseFrames: true },
                        { name: "load", target: window},
                        { name: "unload", target: window},
                        { name: "resize", target: window},
                        { name: "scroll", target: window},
                        { name: "mousemove", recurseFrames: true },
                        { name: "orientationchange", target: window},
                        { name: "touchend" },
                        { name: "touchstart" },
                        { name: "error", target: window}
                    </#if>

                    ]
                }