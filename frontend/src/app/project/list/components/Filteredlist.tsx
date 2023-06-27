import ProjectCard from './ProjectCard';

type optionsProps = {
    options: string[];
  };

export default function Filteredlist({options}: optionsProps): JSX.Element{
    const projects = [
        {
          id: 1,
          projectName: 'Project 1',
          poster: 'name1',
          location: '경기',
          requiredCategory: 'Option 1',
          description: 'This is the description for Project 1',
        },
        {
          id: 2,
          projectName: 'Project 2',
          poster: 'name2',
          location: '서울',
          requiredCategory: 'Option 2',
          description: 'This is the description for Project 2',
        },
        {
          id: 3,
          projectName: 'Project 3',
          poster: 'name3',
          location: '부산',
          requiredCategory: 'Option 3',
          description: 'This is the description for Project 3',
        },
      ];

    const filterbyAll = projects.filter(
    (project) => project.location === options[0] && project.requiredCategory === options[1] 
    )
    const filterbyLocation = projects.filter(
    (project) =>  project.location === options[0] 
    )
    const filterbyCategory = projects.filter(
    (project) => project.requiredCategory === options[1] 
    )
      
    return(
        <div>
            <div className="text-2xl text-left my-2">
                        PROJECTS
            </div>
                <div className="grid grid-cols-3 gap-4 place-content-center">
                {options[0] === "All" && options[1] === "All" ? (
                projects.map((project) => <ProjectCard project={project} key={project.id}/>)
                ) : options[0] !== "All" && options[1] !== "All" ? (
                filterbyAll.map((project) => <ProjectCard project={project} key={project.id}/>)
                ) : options[0] !== "All" && options[1] === "All" ? (
                filterbyLocation.map((project) => <ProjectCard project={project} key={project.id}/>)
                ) : options[0] === "All" && options[1] !== "All" ? (
                filterbyCategory.map((project) => <ProjectCard project={project} key={project.id}/>)
                ) : null}
                </div>
        </div>
        )   
}