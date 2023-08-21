import Banner from '@/components/Banner'
import ProjectSection from '@/components/ProjectSection'
export default function HomePage() {
  return (
    <main>
      <Banner className="min-h-fit" />
      <section>
        <ProjectSection />
      </section>
    </main>
  )
}
