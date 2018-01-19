(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('reservation', {
            parent: 'entity',
            url: '/reservation',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.reservation.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reservation/reservations.html',
                    controller: 'ReservationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('reservation');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('reservation-detail', {
            parent: 'reservation',
            url: '/reservation/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.reservation.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reservation/reservation-detail.html',
                    controller: 'ReservationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('reservation');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Reservation', function($stateParams, Reservation) {
                    return Reservation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'reservation',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('reservation-detail.edit', {
            parent: 'reservation-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reservation/reservation-dialog.html',
                    controller: 'ReservationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Reservation', function(Reservation) {
                            return Reservation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('reservation.new', {
            parent: 'reservation',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reservation/reservation-dialog.html',
                    controller: 'ReservationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                note: null,
                                dateat: null,
                                createdat: null,
                                symtoms: null,
                                medicaments: null,
                                cost: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('reservation', null, { reload: 'reservation' });
                }, function() {
                    $state.go('reservation');
                });
            }]
        })
        .state('reservation.edit', {
            parent: 'reservation',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reservation/reservation-dialog.html',
                    controller: 'ReservationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Reservation', function(Reservation) {
                            return Reservation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reservation', null, { reload: 'reservation' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('reservation.delete', {
            parent: 'reservation',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reservation/reservation-delete-dialog.html',
                    controller: 'ReservationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Reservation', function(Reservation) {
                            return Reservation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reservation', null, { reload: 'reservation' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('appreciation.agregar_valoraracion', {
            parent: 'reservation',
            url: '/valoracion',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/appreciation/appreciation-dialog2.html',
                    controller: 'AppreciationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                height: null,
                                weight: null,
                                size: null,
                                bmi: null,
                                temperature: null,
                                saturation: null,
                                bloodpressuere: null,
                                heartrate: null,
                                breathingfrequency: null,
                                others: null,
                                head: null,
                                neck: null,
                                chest: null,
                                abdomen: null,
                                bodypart: null,
                                genitals: null,
                                othersphysical: null,
                                createdat: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('reservation', null, { reload: 'reservation' });
                }, function() {
                    $state.go('reservation');
                });
            }]
        })
        .state('appreciation.editar_valoracion', {
            parent: 'reservation',
            url: '/{id}/valoracion',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/appreciation/appreciation-dialog2.html',
                    controller: 'AppreciationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Appreciation', function(Appreciation) {
                            return Appreciation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reservation', null, { reload: 'reservation' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        ;
    }

})();
