(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('reservation-calendar', {
            parent: 'entity',
            url: '/reservation-calendar',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.reservation-calendar.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reservation-calendar/reservations-calendar.html',
                    controller: 'ReservationCalendarController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('reservationCalendar');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
         })
        //.state('reservation-detail', {
        //     parent: 'reservation',
        //     url: '/reservation/{id}',
        //     data: {
        //         authorities: ['ROLE_USER'],
        //         pageTitle: 'cercardiobitiApp.reservation.detail.title'
        //     },
        //     views: {
        //         'content@': {
        //             templateUrl: 'app/entities/reservation/reservation-detail.html',
        //             controller: 'ReservationDetailController',
        //             controllerAs: 'vm'
        //         }
        //     },
        //     resolve: {
        //         translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
        //             $translatePartialLoader.addPart('reservation');
        //             return $translate.refresh();
        //         }],
        //         entity: ['$stateParams', 'Reservation', function($stateParams, Reservation) {
        //             return Reservation.get({id : $stateParams.id}).$promise;
        //         }],
        //         previousState: ["$state", function ($state) {
        //             var currentStateData = {
        //                 name: $state.current.name || 'reservation',
        //                 params: $state.params,
        //                 url: $state.href($state.current.name, $state.params)
        //             };
        //             return currentStateData;
        //         }]
        //     }
        // })
        // .state('reservation-detail.edit', {
        //     parent: 'reservation-detail',
        //     url: '/detail/edit',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/reservation/reservation-dialog.html',
        //             controller: 'ReservationDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: ['Reservation', function(Reservation) {
        //                     return Reservation.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('^', {}, { reload: false });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        .state('reservation-calendar.new', {
            parent: 'reservation-calendar',
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
                                timeat: null,
                                createdat: null,
                                symtoms: null,
                                medicaments: null,
                                cost: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('reservationCalendar', null, { reload: 'reservationCalendar' });
                }, function() {
                    $state.go('reservationCalendar');
                });
            }]
        })
        .state('reservation-calendar.edit', {
            parent: 'reservation-calendar',
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
                        entity: ['ReservationCalendar', function(Reservation) {
                            return Reservation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reservationCalendar', null, { reload: 'reservationCalendar' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        // .state('reservation.delete', {
        //     parent: 'reservation',
        //     url: '/{id}/delete',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/reservation/reservation-delete-dialog.html',
        //             controller: 'ReservationDeleteController',
        //             controllerAs: 'vm',
        //             size: 'md',
        //             resolve: {
        //                 entity: ['Reservation', function(Reservation) {
        //                     return Reservation.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('reservation', null, { reload: 'reservation' });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        ;
    }

})();
