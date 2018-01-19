(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('diagnosis', {
            parent: 'entity',
            url: '/diagnosis',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.diagnosis.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/diagnosis/diagnoses.html',
                    controller: 'DiagnosisController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('diagnosis');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('diagnosis-detail', {
            parent: 'diagnosis',
            url: '/diagnosis/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.diagnosis.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/diagnosis/diagnosis-detail.html',
                    controller: 'DiagnosisDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('diagnosis');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Diagnosis', function($stateParams, Diagnosis) {
                    return Diagnosis.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'diagnosis',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('diagnosis-detail.edit', {
            parent: 'diagnosis-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/diagnosis/diagnosis-dialog.html',
                    controller: 'DiagnosisDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Diagnosis', function(Diagnosis) {
                            return Diagnosis.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('diagnosis.new', {
            parent: 'diagnosis',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/diagnosis/diagnosis-dialog.html',
                    controller: 'DiagnosisDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                letra: null,
                                catalogkey: null,
                                asterisco: null,
                                nombre: null,
                                lsex: null,
                                linf: null,
                                lsup: null,
                                trivial: null,
                                erradicado: null,
                                ninter: null,
                                nin: null,
                                ninmtobs: null,
                                nocbd: null,
                                noaph: null,
                                fetal: null,
                                capitulo: null,
                                lista1: null,
                                grupo1: null,
                                lista5: null,
                                actualizacionescie10: null,
                                yearmodifi: null,
                                yearaplicacion: null,
                                valid: null,
                                prinmorta: null,
                                prinmorbi: null,
                                lmmorbi: null,
                                lmmorta: null,
                                lgbd165: null,
                                iomsbeck: null,
                                lgbd190: null,
                                notdiaria: null,
                                notsemanal: null,
                                sistemaespecial: null,
                                birmm: null,
                                causatype: null,
                                epimorta: null,
                                edasiras5Anios: null,
                                csvematernasseedepid: null,
                                epimortam5: null,
                                epimorbi: null,
                                defmaternas: null,
                                escauses: null,
                                numcauses: null,
                                essuivemorta: null,
                                essuivemorb: null,
                                epiclave: null,
                                epiclavedesc: null,
                                essuivenotin: null,
                                essuiveestepi: null,
                                essuiveestbrote: null,
                                sinac: null,
                                daga: null,
                                manifestaenfer: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('diagnosis', null, { reload: 'diagnosis' });
                }, function() {
                    $state.go('diagnosis');
                });
            }]
        })
        .state('diagnosis.edit', {
            parent: 'diagnosis',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/diagnosis/diagnosis-dialog.html',
                    controller: 'DiagnosisDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Diagnosis', function(Diagnosis) {
                            return Diagnosis.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('diagnosis', null, { reload: 'diagnosis' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('diagnosis.delete', {
            parent: 'diagnosis',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/diagnosis/diagnosis-delete-dialog.html',
                    controller: 'DiagnosisDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Diagnosis', function(Diagnosis) {
                            return Diagnosis.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('diagnosis', null, { reload: 'diagnosis' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
