(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pacient-contact', {
            parent: 'entity',
            url: '/pacient-contact',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.pacientContact.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pacient-contact/pacient-contacts.html',
                    controller: 'PacientContactController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pacientContact');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('pacient-contact-detail', {
            parent: 'pacient-contact',
            url: '/pacient-contact/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.pacientContact.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pacient-contact/pacient-contact-detail.html',
                    controller: 'PacientContactDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pacientContact');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PacientContact', function($stateParams, PacientContact) {
                    return PacientContact.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pacient-contact',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pacient-contact-detail.edit', {
            parent: 'pacient-contact-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-contact/pacient-contact-dialog.html',
                    controller: 'PacientContactDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PacientContact', function(PacientContact) {
                            return PacientContact.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pacient-contact.new', {
            parent: 'pacient-contact',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-contact/pacient-contact-dialog.html',
                    controller: 'PacientContactDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                lastname: null,
                                street: null,
                                streetnumber: null,
                                suitnumber: null,
                                phonenumber1: null,
                                phonenumber2: null,
                                email1: null,
                                email2: null,
                                facebook: null,
                                twitter: null,
                                instagram: null,
                                snapchat: null,
                                linkedin: null,
                                vine: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pacient-contact', null, { reload: 'pacient-contact' });
                }, function() {
                    $state.go('pacient-contact');
                });
            }]
        })
        .state('pacient-contact.edit', {
            parent: 'pacient-contact',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-contact/pacient-contact-dialog.html',
                    controller: 'PacientContactDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PacientContact', function(PacientContact) {
                            return PacientContact.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pacient-contact', null, { reload: 'pacient-contact' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pacient-contact.delete', {
            parent: 'pacient-contact',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-contact/pacient-contact-delete-dialog.html',
                    controller: 'PacientContactDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PacientContact', function(PacientContact) {
                            return PacientContact.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pacient-contact', null, { reload: 'pacient-contact' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
