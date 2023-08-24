import Banner from '@/components/Banner'
import ProjectSection from '@/components/ProjectSection'
import RandomSection from '@/components/RandomSection'

export default function HomePage() {
  return (
    <main>
      <Banner className="min-h-fit" />
      <section>
        <ProjectSection />
      </section>
      <section>
        <RandomSection />
      </section>
    </main>
  )
}
