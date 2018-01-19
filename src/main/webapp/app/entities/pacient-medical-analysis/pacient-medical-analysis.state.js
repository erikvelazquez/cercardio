(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pacient-medical-analysis', {
            parent: 'entity',
            url: '/pacient-medical-analysis',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.pacientMedicalAnalysis.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pacient-medical-analysis/pacient-medical-analyses.html',
                    controller: 'PacientMedicalAnalysisController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pacientMedicalAnalysis');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('pacient-medical-analysis-detail', {
            parent: 'pacient-medical-analysis',
            url: '/pacient-medical-analysis/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.pacientMedicalAnalysis.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pacient-medical-analysis/pacient-medical-analysis-detail.html',
                    controller: 'PacientMedicalAnalysisDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pacientMedicalAnalysis');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PacientMedicalAnalysis', function($stateParams, PacientMedicalAnalysis) {
                    return PacientMedicalAnalysis.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pacient-medical-analysis',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pacient-medical-analysis-detail.edit', {
            parent: 'pacient-medical-analysis-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-medical-analysis/pacient-medical-analysis-dialog.html',
                    controller: 'PacientMedicalAnalysisDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PacientMedicalAnalysis', function(PacientMedicalAnalysis) {
                            return PacientMedicalAnalysis.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pacient-medical-analysis.new', {
            parent: 'pacient-medical-analysis',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-medical-analysis/pacient-medical-analysis-dialog.html',
                    controller: 'PacientMedicalAnalysisDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                presentation: null,
                                subjective: null,
                                objective: null,
                                analysis: null,
                                disease: null,
                                tests: null,
                                treatment: null,
                                medicine: null,
                                daytime: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pacient-medical-analysis', null, { reload: 'pacient-medical-analysis' });
                }, function() {
                    $state.go('pacient-medical-analysis');
                });
            }]
        })
        .state('pacient-medical-analysis.edit', {
            parent: 'pacient-medical-analysis',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-medical-analysis/pacient-medical-analysis-dialog.html',
                    controller: 'PacientMedicalAnalysisDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PacientMedicalAnalysis', function(PacientMedicalAnalysis) {
                            return PacientMedicalAnalysis.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pacient-medical-analysis', null, { reload: 'pacient-medical-analysis' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pacient-medical-analysis.delete', {
            parent: 'pacient-medical-analysis',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-medical-analysis/pacient-medical-analysis-delete-dialog.html',
                    controller: 'PacientMedicalAnalysisDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PacientMedicalAnalysis', function(PacientMedicalAnalysis) {
                            return PacientMedicalAnalysis.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pacient-medical-analysis', null, { reload: 'pacient-medical-analysis' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
