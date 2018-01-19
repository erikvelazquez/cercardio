(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('medical-analysis', {
            parent: 'entity',
            url: '/medical-analysis',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.medicalAnalysis.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medical-analysis/medical-analyses.html',
                    controller: 'MedicalAnalysisController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('medicalAnalysis');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('medical-analysis-detail', {
            parent: 'medical-analysis',
            url: '/medical-analysis/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.medicalAnalysis.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medical-analysis/medical-analysis-detail.html',
                    controller: 'MedicalAnalysisDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('medicalAnalysis');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'MedicalAnalysis', function($stateParams, MedicalAnalysis) {
                    return MedicalAnalysis.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'medical-analysis',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('medical-analysis-detail.edit', {
            parent: 'medical-analysis-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medical-analysis/medical-analysis-dialog.html',
                    controller: 'MedicalAnalysisDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MedicalAnalysis', function(MedicalAnalysis) {
                            return MedicalAnalysis.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medical-analysis.new', {
            parent: 'medical-analysis',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medical-analysis/medical-analysis-dialog.html',
                    controller: 'MedicalAnalysisDialogController',
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
                    $state.go('medical-analysis', null, { reload: 'medical-analysis' });
                }, function() {
                    $state.go('medical-analysis');
                });
            }]
        })
        .state('medical-analysis.edit', {
            parent: 'medical-analysis',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medical-analysis/medical-analysis-dialog.html',
                    controller: 'MedicalAnalysisDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MedicalAnalysis', function(MedicalAnalysis) {
                            return MedicalAnalysis.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medical-analysis', null, { reload: 'medical-analysis' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medical-analysis.delete', {
            parent: 'medical-analysis',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medical-analysis/medical-analysis-delete-dialog.html',
                    controller: 'MedicalAnalysisDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MedicalAnalysis', function(MedicalAnalysis) {
                            return MedicalAnalysis.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medical-analysis', null, { reload: 'medical-analysis' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
