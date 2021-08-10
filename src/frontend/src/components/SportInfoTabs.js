import React, {useState} from 'react';
import "./SportInfoTabs.css"

export const SportInfoTabs = ({tabs}) => {

    const [toggleState, setToggleState] = useState(0);

    const toggleTab = (tabIndex) => {
        setToggleState(tabIndex);
    }


    return (
        <div className="container">
            {tabs.length > 1 ? <div className="bloc-tabs">
                {tabs.map((tab, tabIndex) => {
                    return (
                        <button
                            className={toggleState === tabIndex ? "tabs active-tabs" : "tabs"}
                            onClick={() => toggleTab(tabIndex)}
                        >
                            {tab.tabName}
                        </button>
                    )
                })}
            </div> : ""}

            <div className="content-tabs">
                {tabs.map((tab, tabIndex) => {
                    return (
                        <div className={toggleState === tabIndex ? "content  active-content" : "content"}>
                            {tab.articleList.map(article => {
                                return (
                                    <div
                                        className={toggleState === tabIndex ? "content  active-content" : "content"}
                                    >
                                        <h2>{article.title}</h2>
                                        <hr/>
                                        {article.contents.map(content => {
                                            return (
                                                <p>
                                                    {content}
                                                </p>
                                            )
                                        })}
                                    </div>
                                )
                            })}
                        </div>
                    )
                })
                }
            </div>
        </div>
    )
}